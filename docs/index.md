# Getting started

A simple guide to have a shader up in no time!

## Installation

To use this library, you have to install it with [jitpack](https://jitpack.io/).

To do so you can add the [jitpack](https://jitpack.io/) repository to your `build.gradle`:

```
maven {
        url "https://jitpack.io"
        content {
            includeGroupAndSubgroups("com.github")
        }}
```

And add the following implementation to your mod:

```
implementation 'com.github.PadjokeJ:phlox:1.0.0'

```
## Registering a shader

To register a new shader, you simply have to register it to the `ShaderRegistry` class:

```
ShaderRegistry.register(() -> true, Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "shader_id")));
```

The first parameter is a condition on which the shader should be rendered. The second parameter is the identifier of the shader. Its resource file should be located in the `resources/assets/mod_id/post_effect/shader_id.json` folder, and the fragment shader within `resources/assets/mod_id/shaders/shader_id.fsh`.

