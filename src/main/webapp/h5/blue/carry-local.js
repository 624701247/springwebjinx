var carry = {}

!function(mgr) {
    mgr.run = function() {
        console.log('local test')
    }
}(carry)

carry.run()