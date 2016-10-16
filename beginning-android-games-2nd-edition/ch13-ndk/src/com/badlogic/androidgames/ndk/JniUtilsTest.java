package com.badlogic.androidgames.ndk;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.JniUtils;
import com.badlogic.androidgames.framework.Screen;
import com.badlogic.androidgames.framework.impl.GLGame;
import com.badlogic.androidgames.framework.impl.GLScreen;

public class JniUtilsTest extends GLGame {

	public Screen getStartScreen() {
	    return new JniUtilsScreen(this);
    }
	
	class JniUtilsScreen extends GLScreen {
		public JniUtilsScreen(Game game) {
	        super(game);
	        float[] values = { 1.231f, 554.3f, 348.6f, 499.3f };
	        ByteBuffer buffer = ByteBuffer.allocateDirect(3 * 4);
	        buffer.order(ByteOrder.nativeOrder());
	        
	        JniUtils.copy(buffer, values, 1, 3);
	        FloatBuffer floatBuffer = buffer.asFloatBuffer();
	        for(int i = 0; i < 3; i++) {
	        	JniUtils.log("JniUtilsTest", Float.toString(floatBuffer.get(i)));
	        }
        }

		@Override
        public void update(float deltaTime) {
        }

		@Override
        public void present(float deltaTime) {
        }

		@Override
        public void pause() {
        }

		@Override
        public void resume() {
        }

		@Override
        public void dispose() {
        }
	}
}
