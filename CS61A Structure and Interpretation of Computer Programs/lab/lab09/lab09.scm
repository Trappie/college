;; Scheme ;;

; Q2
(define (over-or-under x y)
  'YOUR-CODE-HERE
    (if (< x y) 
        -1 
        (if (> x y) 
            1 
            0
        )
    )
)

;;; Tests
(over-or-under 1 2)
; expect -1
(over-or-under 2 1)
; expect 1
(over-or-under 1 1)
; expect 0

; Q3
(define (filter f lst)
    (filter_recursive f lst ())
)

; recursive call to finish Q3
(define (filter_recursive f lst target)
    (if (equal? lst ())
        target
        ; check if (car lst) can make f #t, 
        (if (f (car lst))
            (filter_recursive f (cdr lst) (append target (cons (car lst) nil)))
            (filter_recursive f (cdr lst) target)
        )
    )
)

;;; Tests
(define (even? x)
  (= (modulo x 2) 0))
(filter even? '(0 1 1 2 3 5 8))
; expect (0 2 8)

; Q4
(define (make-adder num)
    (define (adder to_add)
        (+ num to_add) 
    )
    adder
)

;;; Tests
(define adder (make-adder 5))
(adder 8)
; expect 13
