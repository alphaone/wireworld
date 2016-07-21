(ns wireworld.game)

(defn neighbors [board [x y]]
  (filter some? (for [cy (range (dec y) (+ y 2))
                      cx (range (dec x) (+ x 2))
                      :when (not= [x y] [cx cy])]
                  (-> board
                      (nth cy nil)
                      (nth cx nil)))))

(defn next-state [neighbors current]
  (let [heads-in-neighborhood (count (filter #(= :h %) neighbors))]
    (case current
      :e :e
      :h :t
      :t :c
      :c (if (contains? #{1 2} heads-in-neighborhood) :h :c))))

(defn advance-cell [board [x y]]
  (next-state (neighbors board [x y]) (-> board (nth y) (nth x))))

(defn advance-line [board y line]
  (vec (map-indexed #(advance-cell board [% y]) line)))

(defn advance-board [board]
  (vec (map-indexed (partial advance-line board) board)))