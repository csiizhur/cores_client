package com.lanen.service.studyplan;

import com.lanen.base.BaseDao;
import com.lanen.model.studyplan.DictReportNumber;

public interface DictReportNumberService extends BaseDao<DictReportNumber>{

	String getNumberByReportName(String reportName);
}
