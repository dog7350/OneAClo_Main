package kr.kro.oneaclo.www.Service.Shop;

import kr.kro.oneaclo.www.DTO.Page.PageRequestDTO;
import kr.kro.oneaclo.www.DTO.Page.PageResponseDTO;
import kr.kro.oneaclo.www.DTO.Shop.ProductCmtDTO;

public interface ProductCmtService {
    PageResponseDTO<ProductCmtDTO> ProductCmtList(PageRequestDTO pageRequestDTO, int pno);
    void CmtDel(ProductCmtDTO dto);
    void CmtModify(ProductCmtDTO dto);
}
