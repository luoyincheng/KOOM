package com.kwai.koom.javaoom.analysis;

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
public interface HeapAnalysisListener {
	/**
	 * Callback on Heap Analyze is triggered.
	 */
	void onHeapAnalysisTrigger();

	/**
	 * Callback on Heap Analyze is done.
	 */
	void onHeapAnalyzed();

	/**
	 * Callback on Heap Analyze is failed.
	 */
	void onHeapAnalyzeFailed();
}
