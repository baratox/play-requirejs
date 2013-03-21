// Production build file
requirejs.config({
    // Use "empty:" to prevent the Optimizer from inlining these libraries within app modules.
    paths: {
        backbone: 'empty:',
        jquery: 'empty:',
        require: 'empty:',
        underscore: 'empty:'
    }
});
