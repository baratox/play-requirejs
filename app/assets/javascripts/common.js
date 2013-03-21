//The build will inline common dependencies into this file.

//For any third party dependencies, like jQuery, place them in the lib folder.

//Configure loading modules from the lib directory,
//except for 'app' ones, which are in a sibling
//directory.
requirejs.config({
    baseUrl: '/assets/javascripts/lib',
    paths: {
        app: '../app',
        
        // JQuery is served mainly from the CDN, falling back to the local file if needed.
        jquery: [
            '//ajax.googleapis.com/ajax/libs/jquery/1.8.0/jquery.min',
            'jquery'
        ]
    },
    shim: {
        backbone: {
            deps: ['jquery', 'underscore'],
            exports: 'Backbone'
        },
        underscore: {
            exports: '_'
        }
    }
});
