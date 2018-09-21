(load 'lab13_extra)

(define test-list (list 1 2 3 4 5))
(define stream (list-to-stream test-list))

;; append an item to the end of the stream
(define (append-stream stream item)
    ; append the item to the stream
    (if (equal? stream ())
        (cons-stream item nil)
        (cons-stream (car stream) (append-stream (cdr-stream stream) item))
    )
)







(print '*****************************)


(define lst '((a . 1) (b . 2) (c . 3)))
(print (add 'd lst))
