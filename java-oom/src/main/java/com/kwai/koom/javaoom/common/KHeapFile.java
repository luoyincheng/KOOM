package com.kwai.koom.javaoom.common;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;
import java.io.IOException;

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
public class KHeapFile implements Parcelable {
	public static final Creator<KHeapFile> CREATOR = new Creator<KHeapFile>() {
		@Override
		public KHeapFile createFromParcel(Parcel in) {
			return new KHeapFile(in);
		}

		@Override
		public KHeapFile[] newArray(int size) {
			return new KHeapFile[size];
		}
	};
	private static final String HPROF_FILE = ".hprof";
	private static final String JSON_FILE = ".json";
	private static final String TAG = "KHeapFile";
	private static KHeapFile kHeapFile;
	public Hprof hprof;
	public Report report;
	private String timeStamp;

	public KHeapFile() {
	}

	protected KHeapFile(Parcel in) {
		hprof = in.readParcelable(Hprof.class.getClassLoader());
		report = in.readParcelable(Report.class.getClassLoader());
	}

	public static void buildInstance(KHeapFile heapFile) {
		kHeapFile = heapFile;
	}

	public static KHeapFile buildInstance(File hprof, File report) {
		kHeapFile = getKHeapFile();
		kHeapFile.hprof = new Hprof(hprof.getAbsolutePath());
		kHeapFile.report = new Report(report.getAbsolutePath());
		return kHeapFile;
	}

	public static KHeapFile getKHeapFile() {
		return kHeapFile == null ? kHeapFile = new KHeapFile() : kHeapFile;
	}

	public static void delete() {
		if (kHeapFile == null) {
			return;
		}
		boolean res = kHeapFile.report.file().delete();
		res = kHeapFile.hprof.file().delete();
	}

	private String getTimeStamp() {
		return timeStamp == null ? timeStamp = KUtils.getTimeStamp() : timeStamp;
	}

	public void buildFiles() {
		this.hprof = generateHprofFile();
		this.report = generateReportFile();
	}

	private void generateDir(String dir) {
		File dirFile = new File(dir);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
	}

	private Hprof generateHprofFile() {
		String name = getTimeStamp() + HPROF_FILE;
		generateDir(KGlobalConfig.getHprofDir());
		return new Hprof(KGlobalConfig.getHprofDir() + File.separator + name);
	}

	private Report generateReportFile() {
		String name = getTimeStamp() + JSON_FILE;
		generateDir(KGlobalConfig.getReportDir());
		Report report = new Report(KGlobalConfig.getReportDir() + File.separator + name);
		if (!report.file().exists()) {
			try {
				boolean res = report.file().createNewFile();
				res = report.file().setWritable(true);
				res = report.file().setReadable(true);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return report;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable(hprof, flags);
		dest.writeParcelable(report, flags);
	}

	public static class Hprof extends BaseFile implements Parcelable {
		public static final Creator<Hprof> CREATOR = new Creator<Hprof>() {
			@Override
			public Hprof createFromParcel(Parcel in) {
				return new Hprof(in);
			}

			@Override
			public Hprof[] newArray(int size) {
				return new Hprof[size];
			}
		};
		public boolean stripped;

		public Hprof(String path) {
			super(path);
		}

		protected Hprof(Parcel in) {
			super(in);
			stripped = in.readByte() != 0;
		}

		public static Hprof file(String path) {
			return new Hprof(path);
		}

		@Override
		public int describeContents() {
			return 0;
		}

		@Override
		public void writeToParcel(Parcel dest, int flags) {
			super.writeToParcel(dest, flags);
			dest.writeByte((byte) (stripped ? 1 : 0));
		}

	}

	public static class Report extends BaseFile {

		public static final Creator<Report> CREATOR = new Creator<Report>() {
			@Override
			public Report createFromParcel(Parcel in) {
				return new Report(in);
			}

			@Override
			public Report[] newArray(int size) {
				return new Report[size];
			}
		};

		public Report(String path) {
			super(path);
		}

		protected Report(Parcel in) {
			super(in);
		}

		public static Report file(String path) {
			return new Report(path);
		}
	}

	private static class BaseFile implements Parcelable {
		public static final Creator<BaseFile> CREATOR = new Creator<BaseFile>() {
			@Override
			public BaseFile createFromParcel(Parcel in) {
				return new BaseFile(in);
			}

			@Override
			public BaseFile[] newArray(int size) {
				return new BaseFile[size];
			}
		};
		public String path;
		private File file;

		private BaseFile(String path) {
			this.path = path;
		}

		protected BaseFile(Parcel in) {
			path = in.readString();
		}

		public String path() {
			return path;
		}

		public File file() {
			//if (file == null) {
			//  KLog.i(TAG, "file():" + path);
			//}
			return file == null ? file = new File(path) : file;
		}

		public void delete() {
			file = file();
			if (file != null) {
				file.delete();
			}
		}

		@Override
		public int describeContents() {
			return 0;
		}

		@Override
		public void writeToParcel(Parcel dest, int flags) {
			dest.writeString(path);
		}

	}


}
