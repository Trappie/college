(define (make-adder num)
    (define (adder to_add)
        (+ num to_add) 
    )
    adder
)

(define adder (make-adder 5))
adder
;(adder 8)
