(ns wireworld.db)

(def initial-x 60)
(def initial-y 30)

(def default-db
  {:count 0
   :to    nil
   :board (->> (repeat initial-x :e)
               vec
               (repeat initial-y)
               vec)})
