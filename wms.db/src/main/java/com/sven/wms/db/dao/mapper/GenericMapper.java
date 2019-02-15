package com.sven.wms.db.dao.mapper;



import com.sven.wms.core.entity.vo.LowerCaseResultMap;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by sven on 2017/3/18.
 */
public interface GenericMapper {

    List<LowerCaseResultMap> executeSql(String sql);

    int executeInsertSql(String sql);

    int executeUpdateSql(String sql);

    int executeDeleteSql(String sql);

    int selectCountSql(String sql);

    Object selectOneResult(String sql);

    List<LowerCaseResultMap> executeDynamicSql(Map<String, Object> params);

    int insertDynamicTable(Map<String, Object> params);

    int executeDynamicSelectInsert(Map<String, Object> params);

    int updateDynamicTable(Map<String, Object> params);

    int deleteDynamicTable(Map<String, Object> params);
    
    void callProcedure(String procedureName);

}
