package com.kwai.koom.demo.leaked;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

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
public abstract class LeakMaker<T> {
	private static List<LeakMaker> leakMakerList = new ArrayList<>();
	List<T> uselessObjectList = new ArrayList<>();

	public static void makeLeak(Context context) {
		leakMakerList.add(new ActivityLeakMaker());
		leakMakerList.add(new BitmapLeakMaker());
		leakMakerList.add(new ByteArrayLeakMaker());
		leakMakerList.add(new FragmentLeakMaker());
		leakMakerList.add(new StringLeakMaker());
		for (LeakMaker leakMaker : leakMakerList) {
			leakMaker.startLeak(context);
		}
	}

	abstract void startLeak(Context context);
}
