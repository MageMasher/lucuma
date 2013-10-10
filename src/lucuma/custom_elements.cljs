(ns lucuma.custom-elements
  (:require [clojure.string :as string]))

(def ^:private forbidden-names #{"annotation-xml" "color-profile" "font-face" "font-face-src" "font-face-uri" "font-face-format" "font-face-name" "missing-glyph"})

(defn valid-name?
  "return true if provided name is a valid Custom Element name"
  [s]
  (and (not= -1 (.indexOf s "-"))
       (not (contains? forbidden-names s))))

(defn find-prototype
  [t]
  (if t
    (.getPrototypeOf js/Object (.createElement js/document t))
    (.-prototype js/HTMLElement)))

(defn default-constructor-name
  [n]
  (let [v (string/split n #"-")]
    (str (string/upper-case (get v 0)) (string/join (map string/capitalize (subvec v 1))))))

;; chrome tests: https://chromium.googlesource.com/chromium/blink/+/master/LayoutTests/fast/dom/custom/
;; https://github.com/w3c/web-platform-tests