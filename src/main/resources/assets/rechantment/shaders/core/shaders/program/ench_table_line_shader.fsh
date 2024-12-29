#version 150

uniform sampler2D MainDiffuse;
uniform sampler2D LineEffectMap;

uniform vec3 ColorModulator;
uniform vec2 Resolution;
uniform float Time;

in vec2 texCoord;
in vec4 vertexColor;

out vec4 fragColor;

float inverseLerp(float v, float minValue, float maxValue) {
    return (v - minValue) / (maxValue - minValue);
}

float remap(float v, float inMin, float inMax, float outMin, float outMax) {
    float t = inverseLerp(v, inMin, inMax);
    return mix(outMin, outMax, t);
}

// The MIT License
// Copyright © 2013 Inigo Quilez
// Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions: The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software. THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
// https://www.youtube.com/c/InigoQuilez
// https://iquilezles.org/
//
// https://www.shadertoy.com/view/Xsl3Dl
vec3 hash( vec3 p ) // replace this by something better
{
    p = vec3( dot(p,vec3(127.1,311.7, 74.7)),
    dot(p,vec3(269.5,183.3,246.1)),
    dot(p,vec3(113.5,271.9,124.6)));

    return -1.0 + 2.0*fract(sin(p)*43758.5453123);
}

float noise( in vec3 p )
{
    vec3 i = floor( p );
    vec3 f = fract( p );

    vec3 u = f*f*(3.0-2.0*f);

    return mix( mix( mix( dot( hash( i + vec3(0.0,0.0,0.0) ), f - vec3(0.0,0.0,0.0) ),
    dot( hash( i + vec3(1.0,0.0,0.0) ), f - vec3(1.0,0.0,0.0) ), u.x),
    mix( dot( hash( i + vec3(0.0,1.0,0.0) ), f - vec3(0.0,1.0,0.0) ),
    dot( hash( i + vec3(1.0,1.0,0.0) ), f - vec3(1.0,1.0,0.0) ), u.x), u.y),
    mix( mix( dot( hash( i + vec3(0.0,0.0,1.0) ), f - vec3(0.0,0.0,1.0) ),
    dot( hash( i + vec3(1.0,0.0,1.0) ), f - vec3(1.0,0.0,1.0) ), u.x),
    mix( dot( hash( i + vec3(0.0,1.0,1.0) ), f - vec3(0.0,1.0,1.0) ),
    dot( hash( i + vec3(1.0,1.0,1.0) ), f - vec3(1.0,1.0,1.0) ), u.x), u.y), u.z );
}

float fbm(vec3 p, int octaves, float persistence, float lacunarity) {
    float amplitude = 1.0;
    float frequency = 1.0;
    float total = 0.0;
    float normalization = 0.0;

    for (int i = 0; i < octaves; ++i) {
        float noiseValue = noise(p * frequency);
        total += noiseValue * amplitude;
        normalization += amplitude;
        amplitude *= persistence;
        frequency *= lacunarity;
    }

    total /= normalization;
    total = smoothstep(-1.0, 1.0, total);

    return total;
}

// This is essentially taking the absolute value of each successive sample (in each octave), and inverting it.
float ridgedFBM(vec3 p, int octaves, float persistence, float lacunarity) {
    float amplitude = 1.0;
    float frequency = 1.0;
    float total = 0.0;
    float normalization = 0.0;

    for (int i = 0; i < octaves; ++i) {
        float noiseValue = noise(p * frequency);
        noiseValue = abs(noiseValue);
        noiseValue = 1.0 - noiseValue;
        total += noiseValue * amplitude;
        normalization += amplitude;
        amplitude *= persistence;
        frequency *= lacunarity;
    }

    total /= normalization;
    //total = smoothstep(-1.0, 1.0, total);
    total *= total;

    return total;
}

float stepped(float noiseSample) {
    float steppedSample = floor(noiseSample * 10.0) / 10.0;
    float remainder = fract(noiseSample * 10.0);
    steppedSample = (steppedSample - remainder) * 0.5 + 0.5;
    return steppedSample;
}

void main() {
    vec4 mainDiffuseColor = texture(MainDiffuse, texCoord);
    vec4 lineBaseColor = texture(LineEffectMap, texCoord);
    vec2 pixelCoords = texCoord * Resolution;
    pixelCoords = pixelCoords - fract(pixelCoords);

    vec3 coords = vec3(pixelCoords / 100.0f, Time * 0.01);
    float noiseSample = ridgedFBM(coords, 2, 0.5, 2.0);
    noiseSample = stepped(noiseSample);

    fragColor = vec4(lineBaseColor * noiseSample);

    // This is such a stupid fucking way of doing this without proper blending but it works I guess.
    // Also makes some parts of the effect texture not have the noise.
    if (lineBaseColor.a <= 0.01) {
        discard;
    }
    if (lineBaseColor.a <= 0.95) {
        fragColor = vec4(lineBaseColor.xyz, 1.0);
    }
    //fragColor = vec4(texCoord, 0.0, 1.0);
}