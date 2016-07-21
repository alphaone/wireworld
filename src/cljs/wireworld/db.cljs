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

;; -- Local Storage  ----------------------------------------------------------

(def lsk "wireworld")     ;; localstore key

(defn ls->board
  "Read in board from LS, and process into a map we can merge into app-db."
  []
  (some->> (.getItem js/localStorage lsk)
           (cljs.reader/read-string)   ;; stored as an EDN
           (hash-map :board)))

(defn board->ls!
  "Puts board into localStorage"
  [board]
  (.setItem js/localStorage lsk (str board)))   ;; sorted-map writen as an EDN map