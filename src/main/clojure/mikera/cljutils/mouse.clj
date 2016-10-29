(ns mikera.cljutils.mouse
  (:import [java.awt MouseInfo PointerInfo Point]))

(set! *warn-on-reflection* true)
(set! *unchecked-math* :warn-on-boxed)

(defn- mouse-point 
  (^Point []
    (.getLocation (MouseInfo/getPointerInfo))))

(defn mouse-pos 
  "Polls the current mouse position relative to the screen"
  ([]
    (let [pt (mouse-point)]
      [(long (.x pt)) (long (.y pt))])))

(defn mouse-x 
  "Polls the current mouse x-position relative to the screen"
  (^long []
    (let [pt (mouse-point)]
      (long (.x pt)))))

(defn mouse-y 
  "Polls the current mouse y-position relative to the screen"
  (^long []
    (let [pt (mouse-point)]
      (long (.y pt)))))