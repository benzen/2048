# deux-mile-quarante-huit

A simple 2048 clone, made with clojurescript and reagent

##Rational

I just dicovered csp style, and core.async.
In the process I've seen a lot of clojure and clojurescript.
To me it seems that there is a lot to explorer on the clojure galaxy.

This pet projet, is meerly a way to check if I can tolerate cl/cljs enought 
and get some of the benefits that cl/cljs seems to offer.

## Setup

Install leiningen  see http://leiningen.org/#install
  
First-time Clojurescript developers, add the following to your bash .profile:

    export LEIN_FAST_TRAMPOLINE=y
    alias cljsbuild="lein trampoline cljsbuild $@"

To avoid compiling ClojureScript for each build, AOT Clojurescript locally in your project with the following:

    ./scripts/compile_cljsc

Subsequent dev builds can use:

    lein cljsbuild auto dev

To start a Node REPL (requires rlwrap):

    ./scripts/repl

To get source map support in the Node REPL:

    lein npm install

Clean project specific out:

    lein clean
     
Optimized builds:

    lein cljsbuild once dev 

For more info on Cljs compilation, read [Waitin'](http://swannodette.github.io/2014/12/22/waitin/).

## License

Copyright © 2015 Benjamin Dreux

Distributed under the GNU Public License version 3.0.
