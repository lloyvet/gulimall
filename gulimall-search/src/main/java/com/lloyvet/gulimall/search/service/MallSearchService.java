package com.lloyvet.gulimall.search.service;

import com.lloyvet.gulimall.search.vo.SearchParam;
import com.lloyvet.gulimall.search.vo.SearchResult;

public interface MallSearchService {
    /**
     *
     * @param param 检索的所有参数
     * @return
     */
    SearchResult search(SearchParam param);
}
