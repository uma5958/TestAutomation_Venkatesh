package com.auto.test.Web;

import static com.auto.test.Pages.LoginPage.*;
import org.testng.annotations.Test;
import com.auto.test.Core.Base;

public class TestLoginPage extends Base {

	@Test
	public void TestToVerifyLoginAndLogoutFromTheApplication() throws Exception {
		goToLoginPage();
		verifyAllElementsInLoginPage();
		loginToTheApplication();
		logOutFromTheApplication();
	}

}
