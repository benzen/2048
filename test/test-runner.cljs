(ns test-runner
  (:require [cljs.test :refer-macros [run-tests]]
            [deux-mile-quarante-huit.test]))

(enable-console-print!)

(defn runner []
  (if (cljs.test/successful?
        (run-tests
         'deux-mile-quarante-huit.test))
    0
    1))
