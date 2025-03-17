package com.ccbb.demo.service.common;

import com.ccbb.demo.entity.CmmCodeJpaEntity;
import com.ccbb.demo.repository.CommonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CommonService {
    private final CommonRepository commonRepository;

    public List<CmmCodeJpaEntity> getCmmCode(String code) {
        return commonRepository.findBySysCodeOrderBySeq(code);
    }
}
