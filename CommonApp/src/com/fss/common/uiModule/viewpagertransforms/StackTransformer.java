/*
 * Copyright (c) 2014. Marshal Chen.
 */

package com.fss.common.uiModule.viewpagertransforms;

import android.view.View;

public class StackTransformer extends ABaseTransformer {

	@Override
	protected void onTransform(View view, float position) {
		view.setTranslationX(position < 0 ? 0f : -view.getWidth() * position);
	}

}
