package com.badlogic.androidgames.framework.gl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

import com.badlogic.androidgames.framework.JniUtils;
import com.badlogic.androidgames.framework.impl.GLGraphics;

public class Vertices {
    final GLGraphics glGraphics;
    final boolean hasColor;
    final boolean hasTexCoords;
    final int vertexSize;
    final ByteBuffer vertices;
    final ShortBuffer indices;
    
    public Vertices(GLGraphics glGraphics, int maxVertices, int maxIndices, boolean hasColor, boolean hasTexCoords) {
        this.glGraphics = glGraphics;
        this.hasColor = hasColor;
        this.hasTexCoords = hasTexCoords;
        this.vertexSize = (2 + (hasColor?4:0) + (hasTexCoords?2:0)) * 4;
        
        this.vertices = ByteBuffer.allocateDirect(maxVertices * vertexSize);
        this.vertices.order(ByteOrder.nativeOrder());
        
        if(maxIndices > 0) {
            ByteBuffer buffer = ByteBuffer.allocateDirect(maxIndices * Short.SIZE / 8);
            buffer.order(ByteOrder.nativeOrder());
            this.indices = buffer.asShortBuffer();
        } else {
            this.indices = null;
        }            
    }
    
    public void setVertices(float[] vertices, int offset, int length) {
        JniUtils.copy(this.vertices, vertices, offset, length);
        this.vertices.position(0);
        this.vertices.limit(length * 4);
    }
    
    public void setIndices(short[] indices, int offset, int length) {
        this.indices.clear();
        this.indices.put(indices, offset, length);
        this.indices.flip();
    }
    
	public void bind() {
	    GL10 gl = glGraphics.getGL();
	    
	    gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
	    vertices.position(0);
	    gl.glVertexPointer(2, GL10.GL_FLOAT, vertexSize, vertices);
	    
	    if(hasColor) {
	        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
	        vertices.position(8);
	        gl.glColorPointer(4, GL10.GL_FLOAT, vertexSize, vertices);
	    }
	    
	    if(hasTexCoords) {
	        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	        vertices.position(hasColor?24:8);
	        gl.glTexCoordPointer(2, GL10.GL_FLOAT, vertexSize, vertices);
	    }
	}
	
	public void draw(int primitiveType, int offset, int numVertices) {        
	    GL10 gl = glGraphics.getGL();
	    
	    if(indices!=null) {
	        indices.position(offset);
	        gl.glDrawElements(primitiveType, numVertices, GL10.GL_UNSIGNED_SHORT, indices);
	    } else {
	        gl.glDrawArrays(primitiveType, offset, numVertices);
	    }        
	}
	
	public void unbind() {
	    GL10 gl = glGraphics.getGL();
	    if(hasTexCoords)
	        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	
	    if(hasColor)
	        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
	}
}
