(ns sus.doo-runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [sus.core-test]))

(doo-tests 'sus.core-test)

