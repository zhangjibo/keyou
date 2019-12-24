package cases;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pojo.API;
import pojo.Case;
import utils.DataUtils;

public class RegisterCase extends BaseCase {
    
    @BeforeSuite
    public void init() {
	
        
        super.init();
    }


    @Test(dataProvider = "data", description = "注册测试")
    public void test(API api, Case c) {
	
	super.test(api, c);
    }

    @Override
    public boolean assertSql(Object beforeRequestSqlCheck, Object afterRequestSqlCheck, Case c) {
	if ((Long) beforeRequestSqlCheck == 0 && (Long) afterRequestSqlCheck == 1) {
	    return true;
	} else {
	    return false;
	}
    }

    @AfterSuite
    public void finish() {

	super.finish();
    }

    @DataProvider(name = "data")
    public Object[][] data() throws Exception {
	Object[][] data = DataUtils.getCaseByApiId("1");
	return data;
    }

}
