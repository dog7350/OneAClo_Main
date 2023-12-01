package kr.kro.oneaclo.www.DTO.Board.Page;

import kr.kro.oneaclo.www.DTO.Board.BoardDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class PageResponseDTO<E> {

    private int page;
    private int size;
    private int total;

    private int start;
    private int end;

    private boolean prev;
    private boolean next;

    private List<E> dtoList;

    @Builder(builderMethodName = "withAll")
    public PageResponseDTO(PageRequestDTO pageRequestDTO, List<E> dtoList, int total) {

        if(total <= 0) {
            return;
        }

        this.page = pageRequestDTO.getPage();
        this.size = pageRequestDTO.getSize();

        this.total = total;
        this.dtoList = dtoList;

        this.end = (int)(Math.ceil(this.page/10.0)) * 10; //화면 끝번호
        
        this.start = this.end - 9; // 시작 번호

        int last = (int)(Math.ceil((total/(double)size))); //마지막 번호

        this.end = Math.min(end, last);

        this.prev = this.start > 1;

        this.next = total > this.end * this.size;
    }
}
