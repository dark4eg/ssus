
<div class="bs-callout bs-callout-danger">

### SassC libsass command-line compiler is required

You must have the SassC command-line compiler installed to use this feature.

Please follow the instructions at: <a href="http://github.com/sass/sassc">http://github.com/sass/sassc</a>
to install the compiler for your platform.

### Usage
Compile your files once:
```
$ lein sassc once
```

To delete all the files generated by lein-sassc:
```
$ lein sassc clean
```

To recompile when any changes are made:

```
$ lein auto sassc once
```

### Hooks
The following hooks are supported by lein-sassc:
```
$ lein compile
$ lein clean
```

Because lein-sassc requires a binary to compile Sass, it often won't work on platforms like Heroku which compile the application on their servers. To get around this limitation, commit the generated CSS files and remove

```
:hooks [leiningen.sassc]
```

from project.clj.

</div>

### Managing Your Middleware

Request middleware functions are located under the `sus.middleware` namespace.

This namespace is reserved for any custom middleware for the application. Some default middleware is
already defined here. The middleware is assembled in the `wrap-base` function.

Middleware used for development is placed in the `sus.dev-middleware` namespace found in
the `env/dev/clj/` source path.

### Here are some links to get started

1. [HTML templating](http://www.luminusweb.net/docs/html_templating.md)
2. [Accessing the database](http://www.luminusweb.net/docs/database.md)
3. [Setting response types](http://www.luminusweb.net/docs/responses.md)
4. [Defining routes](http://www.luminusweb.net/docs/routes.md)
5. [Adding middleware](http://www.luminusweb.net/docs/middleware.md)
6. [Sessions and cookies](http://www.luminusweb.net/docs/sessions_cookies.md)
7. [Security](http://www.luminusweb.net/docs/security.md)
8. [Deploying the application](http://www.luminusweb.net/docs/deployment.md)
