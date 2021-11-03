#version 330 core

in vec2 TexCoord;

out vec4 FragColor;

uniform vec4 ourColor;
uniform sampler2D oTexture1;
uniform vec3 col;

void main() {
    FragColor = texture(oTexture1, TexCoord) + vec4(col, 1);
}
