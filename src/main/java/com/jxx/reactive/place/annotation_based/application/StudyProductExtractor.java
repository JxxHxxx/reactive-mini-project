package com.jxx.reactive.place.annotation_based.application;

import com.jxx.reactive.place.annotation_based.dto.ResponseBody;
import com.jxx.reactive.place.common.api.xuni.core.Content;
import com.jxx.reactive.place.common.api.xuni.core.ProductDetail;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudyProductExtractor {
    public List<Content> extractContents(ResponseBody<ProductDetail> body) {
        return body.response().contents();
    }
}
