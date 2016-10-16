package com.badlogic.androidgames.gl3d;

import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLU;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Screen;
import com.badlogic.androidgames.framework.gl.Texture;
import com.badlogic.androidgames.framework.gl.Vertices3;
import com.badlogic.androidgames.framework.impl.GLGame;
import com.badlogic.androidgames.framework.impl.GLScreen;

public class HierarchyTest extends GLGame {

	public Screen getStartScreen() {
		return new HierarchyScreen(this);
	}

	class HierarchyScreen extends GLScreen {
        Vertices3 cube;
        Texture texture;
        HierarchicalObject sun;

        public HierarchyScreen(Game game) {
            super(game);
            cube = createCube();
            texture = new Texture(glGame, "crate.png");

            sun = new HierarchicalObject(cube, false);
            sun.z = -5;

            HierarchicalObject planet = new HierarchicalObject(cube, true);
            planet.x = 3;
            planet.scale = 0.2f;
            sun.children.add(planet);

            HierarchicalObject moon = new HierarchicalObject(cube, true);
            moon.x = 1;
            moon.scale = 0.1f;
            planet.children.add(moon);
        }
        
        private Vertices3 createCube() {
            float[] vertices = { -0.5f, -0.5f,  0.5f, 0, 1,
                                  0.5f, -0.5f,  0.5f, 1, 1,
                                  0.5f,  0.5f,  0.5f, 1, 0,
                                 -0.5f,  0.5f,  0.5f, 0, 0,
                               
                                  0.5f, -0.5f,  0.5f, 0, 1,
                                  0.5f, -0.5f, -0.5f, 1, 1,
                                  0.5f,  0.5f, -0.5f, 1, 0,
                                  0.5f,  0.5f,  0.5f, 0, 0,
                                
                                  0.5f, -0.5f, -0.5f, 0, 1,
                                 -0.5f, -0.5f, -0.5f, 1, 1,
                                 -0.5f,  0.5f, -0.5f, 1, 0,
                                  0.5f,  0.5f, -0.5f, 0, 0,
                                
                                 -0.5f, -0.5f, -0.5f, 0, 1, 
                                 -0.5f, -0.5f,  0.5f, 1, 1,
                                 -0.5f,  0.5f,  0.5f, 1, 0,
                                 -0.5f,  0.5f, -0.5f, 0, 0,
                               
                                 -0.5f,  0.5f,  0.5f, 0, 1,
                                  0.5f,  0.5f,  0.5f, 1, 1,
                                  0.5f,  0.5f, -0.5f, 1, 0,
                                 -0.5f,  0.5f, -0.5f, 0, 0,
                               
                                 -0.5f, -0.5f,  0.5f, 0, 1,
                                  0.5f, -0.5f,  0.5f, 1, 1,
                                  0.5f, -0.5f, -0.5f, 1, 0,
                                 -0.5f, -0.5f, -0.5f, 0, 0
            };             
            
            short[] indices = { 0, 1, 3, 1, 2, 3,
                                4, 5, 7, 5, 6, 7,
                                8, 9, 11, 9, 10, 11,
                                12, 13, 15, 13, 14, 15,
                                16, 17, 19, 17, 18, 19,
                                20, 21, 23, 21, 22, 23,
            };
            
            Vertices3 cube = new Vertices3(glGraphics, 24, 36, false, true);
            cube.setVertices(vertices, 0, vertices.length);
            cube.setIndices(indices, 0, indices.length);
            return cube;
        }

        @Override
        public void update(float deltaTime) {
            sun.update(deltaTime);
        }

        @Override
        public void present(float deltaTime) {
            GL10 gl = glGraphics.getGL();
            gl.glViewport(0, 0, glGraphics.getWidth(), glGraphics.getHeight());
            gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
            gl.glMatrixMode(GL10.GL_PROJECTION);
            gl.glLoadIdentity();
            GLU.gluPerspective(gl, 67, glGraphics.getWidth()
                    / (float) glGraphics.getHeight(), 0.1f, 10.0f);
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            gl.glLoadIdentity();
            gl.glTranslatef(0, -2, 0);

            gl.glEnable(GL10.GL_DEPTH_TEST);
            gl.glEnable(GL10.GL_TEXTURE_2D);
            texture.bind();
            cube.bind();

            sun.render(gl);

            cube.unbind();
            gl.glDisable(GL10.GL_TEXTURE_2D);
            gl.glDisable(GL10.GL_DEPTH_TEST);
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
