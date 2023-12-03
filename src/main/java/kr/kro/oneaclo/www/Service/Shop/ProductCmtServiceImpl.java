package kr.kro.oneaclo.www.Service.Shop;

import kr.kro.oneaclo.www.DTO.Page.PageRequestDTO;
import kr.kro.oneaclo.www.DTO.Page.PageResponseDTO;
import kr.kro.oneaclo.www.DTO.Shop.ProductCmtDTO;
import kr.kro.oneaclo.www.Entity.Shop.ProductCmt;
import kr.kro.oneaclo.www.Entity.Shop.ProductCmtId;
import kr.kro.oneaclo.www.Repository.Shop.ProductCmtRepository;
import kr.kro.oneaclo.www.Repository.Shop.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductCmtServiceImpl implements ProductCmtService {
    private final ProductCmtRepository productCmtRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public PageResponseDTO<ProductCmtDTO> ProductCmtList(PageRequestDTO pageRequestDTO, int pno) {
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() <=0? 0: pageRequestDTO.getPage()-1, 5, Sort.by("cno").ascending());

        Page<ProductCmt> result = productCmtRepository.SearchProductCmt(pageable,pno);
        List<ProductCmtDTO> dtoList = result.getContent().stream().map(productCmt -> modelMapper.map(productCmt,ProductCmtDTO.class)).collect(Collectors.toList());

        return PageResponseDTO.<ProductCmtDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
    }
    public void CmtDel(ProductCmtDTO dto) {
        productCmtRepository.deleteById(new ProductCmtId(dto.getPno(), dto.getCno()));
    }

    public void CmtModify(ProductCmtDTO dto) {
        Optional<ProductCmt> result = productCmtRepository.findById(new ProductCmtId(dto.getPno(), dto.getCno()));
        ProductCmt productCmt = result.orElseThrow();
        if(productCmt.getSecret()==null) {
            productCmt.Cmtmodify(dto.getCcontent(),"f");
        }else{
            productCmt.Cmtmodify(dto.getCcontent(), dto.getSecret());
        }
        productCmtRepository.save(productCmt);
    }
}
