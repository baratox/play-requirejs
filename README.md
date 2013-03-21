# baratox/play-requirejs

This project is an experiment with recreating the [requirejs/example-multipage-shim](https://github.com/requirejs/example-multipage-shim) application with Play! Framework 2.1.0. The main goals are:

| Goal          | Dev Mode | Prod Mode | Status |
|---------------|----------|-----------|--------|
| **Modules**   | Served in *full source code*, each in its own file, as they are. | Served after *optimized*: minified and combined into fewer files. | 50% |
| **Libraries** | Served in *full source code* | Served after *minified*. The minified version should be the *official*. | 50% |
| **CDN**       | Served *locally*. | Serve libraries from a CDN, such as [Google's](https://developers.google.com/speed/libraries/devguide); | 50% |

## Running and building

To start the application in development mode, run:

    play run

To start in production mode, run:

    play start

To optimize the scripts using the *Require.Js Optimizer* directly, run:

    node tools/r.js -o tools/build.js

That build command creates an optimized version of the project in a **public/javascripts-built** directory. 
The **common.js** file will contain all the common modules. 
**app/main1.js** will contain the main1-specific modules,
**app/main2.js** will contain the main2-specific modules.

This means that for page 1, after an optimization, the loaded scripts would be:
* common.js
* app/main1.js
* and third-party libraries

## Details

### Modules

In *dev mode*, files are served as they are. Nice.

For *prod mode*, the optimization step needs to be improved. Access to optimizer options are limited to:
* Module names, defined with **requireJs** in [Build.scala](project/Build.scala).
* mainConfigFile, defined with **requireJsShim** in Build.scala.

In order to create the best optimization, we need to be able to specify additional options.
Ideally, we should be able to specify a complete build script, like the [build.js](tools/build.js).

Check the difference with Play's optimization and the custom, optimal solution:

    common.js                          custom build.js
    --------------------------------------------------
    common.js                   common.js
                                app/lib.js
                                app/controller/Base.js
                                app/model/Base.js

    app/main1.js
    --------------------------------------------------
    app/lib.js
    app/controller/Base.js
    app/controller/c1.js        app/controller/c1.js
    app/model/Base.js
    app/model/m1.js             app/model/m1.js
    app/main1.js                app/main1.js

    app/main2.js
    --------------------------------------------------
    app/lib.js
    app/controller/Base.js
    app/controller/c2.js        app/controller/c2.js
    app/model/Base.js
    app/model/m2.js             app/model/m2.js
    app/main2.js                app/main2.js

In order to serve different versions depending on the mode, the [Application](app/controllers/Application.java).javascripts() method is routed from
    GET     /assets/javascripts/*file   controllers.Application.javascripts(file)

## Libraries

I couldn't easily keep both, a full source code for dev and a minified version for prod.
Even if there already is a *public/javascripts-min/lib/jquery.js*, Play's optimization overrides it with its own minified version.

There are two main reasons to use the official minified version rather than Play's:
* Play's minified version of jQuery is ~96kb, against ~32kb for the official.
* Minification takes a considerable time.

One way to do this is to have the minified files in a different folder (to avoid re-minification), and use a different route.
Similar to what is used for the */assets/javascripts*.

## CDN

Serving libraries from a CDN in production is trivial, just setup the paths correctly in the *requirejs.config()*.
See the configuration of jQuery in [common.js](app/assets/javascripts/common.js).

But then, libraries are served from the CDN even in dev mode.