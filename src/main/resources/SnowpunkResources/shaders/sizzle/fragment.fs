//
// This is free and unencumbered software released into the public domain.
//
// Anyone is free to copy, modify, publish, use, compile, sell, or
// distribute this software, either in source code form or as a compiled
// binary, for any purpose, commercial or non-commercial, and by any
// means.
//
// In jurisdictions that recognize copyright laws, the author or authors
// of this software dedicate any and all copyright interest in the
// software to the public domain. We make this dedication for the benefit
// of the public at large and to the detriment of our heirs and
// successors. We intend this dedication to be an overt act of
// relinquishment in perpetuity of all present and future rights to this
// software under copyright law.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
// EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
// MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
// IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR
// OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
// ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
// OTHER DEALINGS IN THE SOFTWARE.
//
// For more information, please refer to http://unlicense.org/
// https://www.shadertoy.com/view/4Xc3DM


varying vec2 v_texCoord;

uniform vec2 u_screenSize;
uniform float u_time;
uniform sampler2D u_texture;
uniform float u_speed;
uniform float u_strength;
uniform float u_distortion;

void main()
{
    vec4 col = texture2D(u_texture, v_texCoord);

    if (col.r > 0.0 || col.g > 0.0 || col.b > 0.0) {
        vec2 uv = gl_FragCoord.xy/u_screenSize;
        float legsx = 1. - length((2. * gl_FragCoord - u_screenSize.xy) / u_screenSize.x);
        float legsy = 1. - length((2. * gl_FragCoord - u_screenSize.xy) / u_screenSize.y);

        col = texture(u_texture, uv + vec2(sin((u_time * u_speed) + uv.y * u_strength) * (u_distortion * legsx), sin((u_time * u_speed) + uv.x * u_strength) * (u_distortion * legsy)));

    }

    gl_FragColor = col;
}