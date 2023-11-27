package kr.kro.oneaclo.www.Service.Shop;

import kr.kro.oneaclo.www.Repository.Shop.ProductCmtRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductCmtServiceImpl implements ProductCmtService {
    private final ProductCmtRepository productCmtRepository;
}
