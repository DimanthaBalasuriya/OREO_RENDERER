#version 330 core

in vec3 position;
in vec2 texture;

out vec2 outTexture;

uniform mat4 transformMatrix;
uniform mat4 projectionMatrix;

void main() {
    gl_Position = projectionMatrix * transformMatrix * vec4(position, 1.0f);
    outTexture = texture;
}
