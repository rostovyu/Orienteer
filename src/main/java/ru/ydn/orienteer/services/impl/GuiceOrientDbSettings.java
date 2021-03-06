package ru.ydn.orienteer.services.impl;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import ru.ydn.wicket.wicketorientdb.OrientDbSettings;

public class GuiceOrientDbSettings extends OrientDbSettings
{

	@Inject
	@Override
	public void setDBUrl(@Named("orientdb.url") String url) {
		super.setDBUrl(url);
	}


	@Inject
	@Override
	public void setDBUserName(@Named("orientdb.db.username") String userName) {
		super.setDBUserName(userName);
	}

	@Inject
	@Override
	public void setDBUserPassword(@Named("orientdb.db.password")String password) {
		super.setDBUserPassword(password);
	}

	@Inject
	@Override
	public void setDBInstallatorUserName(@Named("orientdb.db.installator.username") String userName) {
		super.setDBInstallatorUserName(userName);
	}

	@Inject
	@Override
	public void setDBInstallatorUserPassword(@Named("orientdb.db.installator.password")String password) {
		super.setDBInstallatorUserPassword(password);
	}
	
	
	
}
