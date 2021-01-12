package com.kwai.koom.javaoom.common;

import android.app.Application;

import com.kwai.koom.javaoom.monitor.HeapThreshold;
import com.kwai.koom.javaoom.report.DefaultRunningInfoFetcher;

import java.io.File;

/**
 * Copyright 2020 Kwai, Inc. All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * @author Rui Li <lirui05@kuaishou.com>
 */
public class KGlobalConfig {

	static final String KOOM_DIR = "koom";
	static final String HPROF_DIR = "hprof";
	static final String REPORT_DIR = "report";
	private static KGlobalConfig globalConfig;
	private static String rootDir;
	private static String reportDir;
	private static String hprofDir;
	private Application application;
	private KConfig kConfig;
	private RunningInfoFetcher runningInfoFetcher;
	private KSoLoader soLoader;

	private KGlobalConfig() {
	}

	private static KGlobalConfig getGlobalConfig() {
		return globalConfig == null ? globalConfig = new KGlobalConfig() : globalConfig;
	}

	public static Application getApplication() {
		return getGlobalConfig().application;
	}

	public static void setApplication(Application application) {
		getGlobalConfig().setApplicationInternal(application);
	}

	public static KConfig getKConfig() {
		return getGlobalConfig().kConfig;
	}

	public static void setKConfig(KConfig kConfig) {
		getGlobalConfig().setKConfigInternal(kConfig);
	}

	public static HeapThreshold getHeapThreshold() {
		return getGlobalConfig().kConfig.getHeapThreshold();
	}

	public static String getRootDir() {
		if (rootDir != null) {
			return rootDir;
		}
		return rootDir = getGlobalConfig().kConfig.getRootDir();
	}

	public static void setRootDir(String rootDir) {
		getGlobalConfig().kConfig.setRootDir(rootDir);
	}

	public static String getReportDir() {
		if (reportDir != null) {
			return reportDir;
		}
		return reportDir = getRootDir() + File.separator + REPORT_DIR;
	}

	public static String getHprofDir() {
		if (hprofDir != null) {
			return hprofDir;
		}
		return hprofDir = getRootDir() + File.separator + HPROF_DIR;
	}

	public static RunningInfoFetcher getRunningInfoFetcher() {
		return getGlobalConfig().runningInfoFetcher;
	}

	public static KSoLoader getSoLoader() {
		KSoLoader kSoLoader = getGlobalConfig().soLoader;
		return kSoLoader == null ? getGlobalConfig().soLoader = new DefaultKSoLoader() : kSoLoader;
	}

	public static void setSoLoader(KSoLoader soLoader) {
		getGlobalConfig().soLoader = soLoader;
	}

	public void setApplicationInternal(Application application) {
		this.application = application;
		this.runningInfoFetcher = new DefaultRunningInfoFetcher(application);
	}

	public void setKConfigInternal(KConfig kConfig) {
		this.kConfig = kConfig;
	}

}
