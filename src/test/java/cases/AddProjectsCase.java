package cases;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pojo.API;
import pojo.Case;
import utils.DataUtils;

public class AddProjectsCase extends BaseCase {

    @BeforeSuite
    public void init() {

        super.init();
    }


    @Test(dataProvider = "data", description = "新增项目测试")
    public void test(API api, Case c) {
        
        super.test(api, c);
    }

    
    @Override
    public boolean assertSql(Object beforeRequestSqlCheck, Object afterRequestSqlCheck, Case c) {
	return true;
}

    @AfterSuite
    public void finish() {
       
        super.finish();
    }

    @DataProvider(name = "data")
    public Object[][] data() throws Exception {
	Object[][] data = DataUtils.getCaseByApiId("5");
	return data;
    }

}
