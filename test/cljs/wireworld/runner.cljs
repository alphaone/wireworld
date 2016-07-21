(ns wireworld.runner
    (:require [doo.runner :refer-macros [doo-tests]]
              [wireworld.core-test]
              [wireworld.game-test]))

(doo-tests 'wireworld.core-test)
(doo-tests 'wireworld.game-test)
