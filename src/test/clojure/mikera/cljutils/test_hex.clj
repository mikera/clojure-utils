(ns mikera.cljutils.test-hex
  (:use clojure.test)
  (:use mikera.cljutils.hex))

(deftest test-to-hex-string
  (is (= "cafebabe" (hex-string 3405691582)))
  (is (= "10" (hex-string 16)))
  (is (= "00000010" (hex-string 16 8))))

(deftest test-hex-from-string
  (is (= (seq (bytes-from-hex-string "0xFF 0x00")) [-1 0])))
