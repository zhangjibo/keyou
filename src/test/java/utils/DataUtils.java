package utils;

import java.util.ArrayList;
import java.util.List;

import consts.Const;
import pojo.API;
import pojo.Case;


public class DataUtils {
    

    public static List<Case> caseList = ExcelUtils.readExcel(Const.SRC_PATH, 1, Case.class);
    public static List<API> apiList = ExcelUtils.readExcel(Const.SRC_PATH, 0, API.class);
    

    public static Object[][] getCaseByApiId(String apiId) {

	API wantedAPI = null;
	List<Case> wantedCase = new ArrayList<Case>();

	// 获取wantedAPI
	for (API api : apiList) {
	    // 传入的apiId和遍历的apiId相等
	    if (apiId.equals(api.getId())) {
		wantedAPI = api;
		break;
	    }

	}

	// 获取wantedCase
	for (Case c : caseList) {
	    // 传入的apiId和遍历的apiId相等
	    if (apiId.equals(c.getApiId())) {
		wantedCase.add(c);
	    }
	}

	//3.把wantedAPI和wantedCase合并
	//把API和Case放到数组中,{{API, Case},{API, Case},{API, Case}}
	Object[][] data = new Object[wantedCase.size()][2];
	for (int i = 0; i < wantedCase.size(); i++) {
	    data[i][0] = wantedAPI;
	    data[i][1] = wantedCase.get(i);
	    
	}
	return data;
	
    }

}
