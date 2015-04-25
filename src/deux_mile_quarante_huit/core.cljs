(ns deux-mile-quarante-huit.core
  (:require [reagent.core :as reagent :refer [atom]]))

(enable-console-print!)

(defn random-position [size]
  {:x (rand-int size) :y (rand-int size)})

(defn random-positions [nb-random-position]
  (repeatedly nb-random-position (fn []
                                   (random-position 4) )))
(defn empty-map [nb-square-per-row]
  (repeat nb-square-per-row (repeat nb-square-per-row 0)))

(defn two-d-walk [game-map func]
  (map-indexed (fn [x list-of-values]
                 (map-indexed (fn [y value]
                                (func x y value))
                              list-of-values))
               game-map))

(defn add-2-random [board]
    (let [randoms (random-positions 2)]
      (two-d-walk board (fn [x y value]
                          (if (and (zero? value)
                                   (some (fn [current-pos ] (= current-pos  {:x x :y y})) randoms))
                            2 
                            value)))))

(defn create-board []
  (add-2-random (empty-map 4)))


(defn pad-with-zeros [size l]
  (let [nb-pad (- size (count l))
        zeros (repeat nb-pad 0)]
    (flatten (conj l zeros))))

(defn pad-with-4-zeros [l]
  (pad-with-zeros 4 l))

(defn filter-zeros [line]
  (remove zero? line))

(defn add-pairs [line]
  (loop [l line
         acc (list)]
    (if (empty? l) 
      (reverse acc)
      (let [current (first l)
            ahead   (second l)]
        (if (= current ahead)
          (recur (drop 2 l) (conj acc (* 2 current)))
          (recur (drop 1 l) (conj acc current) ))))))

(defn sweep [line]
  (->> line
       filter-zeros
       add-pairs
       pad-with-4-zeros))

(defn rotate-board [b]
  (map-indexed (fn [index row] 
                 (map (fn [row] 
                        (let [index-complement (- (count row) index 1)]
                            (nth row index-complement))) b)) b))
(defn rotate-board-times [n b]
 (nth (iterate rotate-board b) n))



(def dirToNbRot {:right   0
                 :down    1
                 :left    2
                 :up      3})

(defn slide [direction board]
  (let [rotation (dirToNbRot direction)
        reverseRotation (- 4 rotation)]
    (->> board
         (add-2-random)
         (rotate-board-times rotation)
         (map sweep)
         (rotate-board-times reverseRotation)
         )))

(def background {0    "#eee4da"
                 2    "#eee4da"
                 4    "#ede0c8"
                 8    "#f2b179"
                 16   "#f59563"
                 32   "#f67c5f"
                 64   "#f65e3b"
                 128  "#edcf72"
                 256  "#edcc61"
                 512  "#edc850"
                 1024 "#edc53f"
                 2048 "#edc22e"})

(defn color [value]
  (if (> value 8)
    "#f9f6f2"
    "#776e65"))

(defn square [x y value]
  (let [scale 100]
    [:div {:key (str x "-" y)
           :style {:background (background value)
                   :color (color value)
                   :position "absolute"
                   :width scale 
                   :height scale 
                   :top (+ (* scale x) scale) 
                   :left (+ (* scale y) scale)
                   :border "10px solid #bbada0"
                   :display "flex"
                   :justify-content "center"
                   }}

     [:p {:style {:align-self "center"
                  :font-family "cursive"
                  :font-size "50"
                  :font-weight "bold"}}

      (if-not (zero? value) value) ]]))

(def app-state (atom (create-board)))

(defn detect-end-of-game [board]
  (cond 
    (some (fn [row] 
            (some (fn [value] (= 2048 value)) row)) board)  "YOU WIN"
    (= board
       (slide :left  board)
       (slide :right board)
       (slide :up    board)
       (slide :down  board)) "YOU LOSE"
    ))


(defn board []
  [:div 
    (detect-end-of-game @app-state)
    (map-indexed (fn [x row]
                   [:div {:key x}
                    (map-indexed (fn [y value] 
                                   (square x y value))
                                 row)]) @app-state)])
(def arrow-keys {37 :left 
                 38 :up 
                 39 :right
                 40 :down})

(defn is-arrow-key? [keyCode] (not (nil? (arrow-keys keyCode))))

(defn onKeyDown [keyCode]
  (swap! app-state (fn [state] 
                     (slide (arrow-keys keyCode) state))))


(js/document.addEventListener "keydown" (fn [key-event] 
                                          (if (is-arrow-key? key-event.keyCode) 
                                            (onKeyDown key-event.keyCode))))

(reagent/render [board]
                (js/document.getElementById "app"))

