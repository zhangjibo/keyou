package pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;

import javax.validation.constraints.NotNull;

public class Case {
    
    @Excel(name="用例编号")
    @NotNull
    private int id;
    
    @Excel(name="用例描述")
    @NotNull
    private String detail;
    
    @Excel(name="参数")
    
    private String params;
    
    @Excel(name="接口编号")
    @NotNull
    private String apiId;
    
    @Excel(name="期望响应数据")
    @NotNull
    private String expectedData;
    
    
    @Excel(name="检验SQL")
    private String sqlCheck;


    public Case() {
	super();
    }


    public Case(int id, String detail, String params, String apiId, String expectedData, String sqlCheck) {
	super();
	this.id = id;
	this.detail = detail;
	this.params = params;
	this.apiId = apiId;
	this.expectedData = expectedData;
	this.sqlCheck = sqlCheck;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public String getDetail() {
        return detail;
    }


    public void setDetail(String detail) {
        this.detail = detail;
    }


    public String getParams() {
        return params;
    }


    public void setParams(String params) {
        this.params = params;
    }


    public String getApiId() {
        return apiId;
    }


    public void setApiId(String apiId) {
        this.apiId = apiId;
    }


    public String getExpectedData() {
        return expectedData;
    }


    public void setExpectedData(String expectedData) {
        this.expectedData = expectedData;
    }


    public String getSqlCheck() {
        return sqlCheck;
    }


    public void setSqlCheck(String sqlCheck) {
        this.sqlCheck = sqlCheck;
    }


    @Override
    public String toString() {

	return "Case [id=" + id + ", detail=" + detail + ", params=" + params + ", apiId=" + apiId + ", expectedData="
		+ expectedData + ", sqlCheck=" + sqlCheck + "]";
    }

    

   

}
