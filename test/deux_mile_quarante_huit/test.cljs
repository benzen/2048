
(ns deux-mile-quarante-huit.test
  (:require
    [deux-mile-quarante-huit.core :as core]
    [cljs.test :as test :refer-macros [deftest testing is]]))

(defn between [lb hb v]
  (and 
    (<= lb v)
    (> hb v)))

(deftest random-position
  (let [random (core/random-position 4)] 
    (is (between 0 4 (:x random)))
    (is (between 0 4 (:y random)))))


(deftest add-2-random-on-empty-board
  (let [board (list
                (list 0 0 0 0)
                (list 0 0 0 0)
                (list 0 0 0 0)
                (list 0 0 0 0))
        updated-board (core/add-2-random board)]
    (is (or (= (list 2 2) (remove zero? (flatten updated-board)))
            (= (list 2) (remove zero? (flatten updated-board)))))))

(deftest add-2-random-on-full-board-does-nothing
  (let [board `(
                `(2 2 2 2)
                `(2 2 2 2)
                `(2 2 2 2)
                `(2 2 2 2))
        updated-board (core/add-2-random board)]
    (is (= (flatten board) (remove zero? (flatten updated-board))))))

(deftest empty-map
  (let [m (core/empty-map 4)]
    (is (= 4 (count m)))
    (is (every? (fn [l] (= 4 (count l)))))))

(deftest pad-with-4-zeros-on-empty-list
  (is (= `(0 0 0 0) (core/pad-with-4-zeros `()))))

(deftest add-pairs
  (is (= `() (core/add-pairs `())))
  (is (= `(2) (core/add-pairs `(1 1)))))

(deftest sweep
  (is (= `(0 0 0 0) (core/sweep `(0 0 0 0))))
  (is (= `(0 0 0 2) (core/sweep `(0 1 0 1))))
  (is (= `(0 0 0 1) (core/sweep `(0 0 0 1))))
  (is (= `(0 0 2 2) (core/sweep `(1 1 1 1)))))

(deftest rotate-board
  (let [empty-board (list 
                      (list 0 0 0 0)
                      (list 0 0 0 0)
                      (list 0 0 0 0)
                      (list 0 0 0 0))

        b0 (list 
             (list 0 0 0 0)
             (list 0 0 0 1)
             (list 0 0 1 0)
             (list 0 0 0 0))

        b1 (list 
             (list 0 1 0 0)
             (list 0 0 1 0)
             (list 0 0 0 0)
             (list 0 0 0 0))

        b2 (list 
             (list 0 0 0 0)
             (list 0 1 0 0)
             (list 1 0 0 0)
             (list 0 0 0 0))

        b3 (list
             (list 0 0 0 0)
             (list 0 0 0 0)
             (list 0 1 0 0)
             (list 0 0 1 0))
        ]

    (is (= empty-board (core/rotate-board empty-board)))
    (is (= b1 (core/rotate-board b0)))
    (is (= b2 (core/rotate-board (core/rotate-board b0))))
    (is (= b3 (core/rotate-board (core/rotate-board (core/rotate-board b0)))))
    (is (= b0 (core/rotate-board (core/rotate-board(core/rotate-board (core/rotate-board b0))))))))

(defn eq-or-2 [a b]
  "deep value = 
  and (eq-or-2 0 2) returns true, for ignoring random new "
  (cond 
    (and (= () a b)) true
    (and (seq? a) (seq? b)) (and (eq-or-2 (first a) (first b)) 
                                 (eq-or-2 (rest a) (rest b)))
    :else (or (and (= 0 a) (= 2 b)) (= a b))))

(deftest slide
  (let [input-board (list
                      (list 0 0 2 2)
                      (list 4 8 0 0)
                      (list 64 64 0 0)
                      (list 16 8 4 2))
        expected-board (list
                         (list 0 0 0 4)
                         (list 0 0 4 8)
                         (list 0 0 0 128)
                         (list 16 8 4 2))]
    (is (eq-or-2 expected-board (core/slide :right input-board)))))


