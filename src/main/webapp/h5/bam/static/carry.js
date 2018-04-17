/* interface
var carry = {
    // utils
    isWx: null,
    isIos: null,
    urlParam:null,
    randomInt:null,
    isDemo:null,
    isLocal:null,
    clog:null,

    // dom & css
    addClass:null,
    removeClass:null,
    initEm:null,
    weakHint:null,

    // bgm
    bgm: {
        init:null,
        initInEnterGame:null,
        getIsPlay: null,
        getCurTime: null,
        ctrl: null
    },

    // slide
    slide: {
        init:null,
        addUpEvent:null,
        addDownEvent:null,
        addLeftEvent:null,
        addRightEvent:null
    }

    // 
    shake: {
        turnon:null,
        turnoff:null,
        addTriggerEvent:null,
    }

    // mgr index
    SGameIsOk:null,
    startGame:null
};
*/


var carry = {}

// utils
!function (utils) {
    // 判断是否在微信端内置浏览器
    var checkIsWx = function () {
        var ua = navigator.userAgent.toLowerCase();
        if (ua.match(/MicroMessenger/i) == "micromessenger") {
            return true;
        } else {
            return false;
        }
    }
    var checkIsiOS = function () {
        if (/Mac/i.test(navigator.userAgent)) {
            return true
        }
        return false
    }
    utils.isWx = checkIsWx()
    utils.isIos = checkIsiOS()

    var getUrlParam = function (url) {
        var get = {}
        url = url || window.location.href.toString()
        var q = url.split("?")[1];
        if (typeof (q) == "string") {
            q = q.split("#")[0];
            q = q.split("&");
            for (var i in q) {
                var j = q[i].split("=");
                get[j[0]] = j[1];
            }
        }
        return get
    }
    utils.urlParam = getUrlParam()

    utils.randomInt = function (min, max) {
        var r = Math.random()   //  Math.random() 返回0 -- 1的开区间，即 0 < x < 1
        r = Math.ceil(r * (max - (min - 1)))
        return (r + (min - 1))
    }

    utils.isDemo = (window.location.host == "demo.h5.aiwanpai.com")
    utils.isLocal = (window.location.host.indexOf('aiwanpai') == -1)

    var el_clog = null
    utils.clog = function(txt) {
        if(this.isDemo || this.isLocal) {            
        }
        else {
            return 
        }
        
        if(el_clog == null) {
            el_clog = document.createElement('div')
            el_clog.style.cssText = "position:absolute; z-index:9999; color:white; font-size: 0.4rem;"
            document.body.appendChild(el_clog)
        }

        el_clog.innerHTML += (txt + ' ` ')
    }
} (carry)


// dom & css
!function (docs) {
    docs.addClass = function (elId, cls) {
        cls = ' ' + cls
        var el = null
        if (typeof (elId) == "string") {
            el = document.getElementById(elId)
        }
        else {
            el = elId
        }
        if (el && el.className.indexOf(cls) == -1) {
            el.className += cls
        }
    }

    docs.removeClass = function (elId, cls) {
        cls = ' ' + cls
        var el = null
        if (typeof (elId) == "string") {
            el = document.getElementById(elId)
        }
        else {
            el = elId
        }
        if (el) {
            el.className = el.className.replace(cls, "")
        }
    }

    // 给定dom对象，宽适配，设置对应的em单位
    docs.initEm = function (el, cw_em) {
        if (cw_em == null) {
            cw_em = 7.5
        }
        var ch = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight
        var cw = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth
        var ch_em = ch / cw * cw_em
        el.style.fontSize = (cw / cw_em) + 'px';
    }

    // 弱提示
    docs.weakHint = function(desc) {
        var node = document.createElement('div')
        node.className = "cont-weakhint"
        var str = '';
        str += '<span>' + desc + '</span>'
        node.innerHTML = str;
        document.body.appendChild(node);
        carry.initEm(node)

        setTimeout(function() {
            node.remove()
        }, 500 + 400)
    }

} (carry)


// bgm
carry.bgm = (carry.bgm || {})
!function (bgm, isWx, isIos) {
    var _el_bgm = null;
    var _isPlay = false; 
    var _volume = 0;
    var _isDefPlay = false //是否默认播放

    // 加载 jweixin-1.1.0.js 之前调用
    // @param ispPlay : 是否默认播放
    bgm.init = function (ispPlay, bgmUrl) {
        // autoplay controls
        // <audio id="aud-bgm" src="resource/bg.mp3" preload loop >
        // 您的浏览器不支持 audio 标签。
        // </audio>
        // _el_bgm = document.getElementById('aud-bgm')
        
        _el_bgm = new Audio()
        _el_bgm.src = bgmUrl
        _el_bgm.loop = true //循环播放
        
        // _el_bgm.onplaying = function() {
        //     console.log('ing') //只在播放的时候触发一次
        // }
        // _el_bgm.onprogress = function(ev) {
        //     console.log('ing', ev) //???
        // }

        if (ispPlay == false) {
            return
        }

        _isDefPlay = true
        if (isWx) {
            document.addEventListener('WeixinJSBridgeReady', function () {                
                this.ctrl(true, null, _volume)
            }.bind(this), false);
        }
        else if (isIos) {
        }
        else {
            this.ctrl(true, null, _volume)
            // setTimeout(function() {
            //     this.ctrl(true, null, _volume)
            // }.bind(this), 3000);

        }
        
    }

    //loading页面结束，进入游戏场景时调用
    bgm.initInEnterGame = function() {
        if(_isDefPlay == false) {
            return
        }

        this.ctrl(true, 0, 1)
    }

    bgm.getIsPlay = function () {
        return _isPlay
    }
    // bgm.getCurVolume = function() {
    //     return _el_bgm.volume
    // }
    bgm.getCurTime = function () {
        if (_el_bgm == null) {
            console.error('bgm 还没初始化')
            return
        }
        return _el_bgm.currentTime
    }

    // @param isPlay: 是否播放，若不传该参数怎默认切换播放状态
    // @param playTime: 设置播放时间， 不传值默认继续播放
    // @param volume: 设置播放音量，0 -- 1 不传值默认为 1
    bgm.ctrl = function (isPlay, playTime, volume) {
        if (_el_bgm == null) {
            console.error('bgm 还没初始化')
            return
        }
        if (isPlay == null) {
            isPlay = !this.getIsPlay()
        }

        // 
        if (isPlay) {
            if(volume == null) {
                volume = 1
            }
            if(playTime != null) {
                _el_bgm.currentTime = playTime
            }
            _el_bgm.volume = volume
            _el_bgm.play()

            _volume = volume
        }
        else {
            _el_bgm.pause()
        }
        _isPlay = isPlay

        // 
        var ev = new Event('carry-bgm-change')
        document.dispatchEvent(ev)
    }
    bgm.addChangeEvent = function (func) {
        document.addEventListener('carry-bgm-change', function (ev) {
            // func.call(that)
            func()
        })
    }
} (carry.bgm, carry.isWx, carry.isIos)


// touch
/*eg 
carry.slide.init()
carry.slide.addUpEvent(function() {
    console.log('uu', this.stateLb.text)
}, this)
carry.slide.addDownEvent(function() {
    console.log('dd', this.stateLb.text)
}, this)
carry.slide.addLeftEvent(function() {
    console.log('ll', this.stateLb.text)
}, this)
carry.slide.addRightEvent(function() {
    console.log('rr', this.stateLb.text)
}, this)
*/
carry.slide = (carry.slide || {})
!function (slide) {
    var _gapTime = 1000 //触摸时间低于该值才触发事件
    var _gapPer = 1.2
    var _moveDist = 100 //触摸移动距离大于该值才触发事件
    var _startTp = null
    slide.init = function () {
        document.body.addEventListener('touchstart', function (ev) {
            var touch = ev.touches[0];
            _startTp = {
                sx: touch.clientX,
                sy: touch.clientY,
                t: ev.timeStamp
            }
        }, true);
        document.body.addEventListener('touchend', function (ev) {
            if (ev.timeStamp - _startTp.t > _gapTime) {
                console.log('触摸时间过长')
                _startTp = null
                return
            }

            var touch = ev.changedTouches[0];
            var gapx = touch.clientX - _startTp.sx
            var gapy = touch.clientY - _startTp.sy

            if (Math.abs(gapy) / Math.abs(gapx) > _gapPer) {
                if (gapy > _moveDist) {
                    console.log('down')
                    var ev = new Event('carry-slide-down')
                    document.dispatchEvent(ev)
                }
                else if (gapy < -_moveDist) {
                    console.log('up~~~~~~~~~~~~~~~~~~~')
                    var ev = new Event('carry-slide-up')
                    document.dispatchEvent(ev)
                }
            }
            if (Math.abs(gapx) / Math.abs(gapy) > _gapPer) {
                if (gapx > _moveDist) {
                    console.log('right')
                    var ev = new Event('carry-slide-right')
                    document.dispatchEvent(ev)
                }
                else if (gapx < -_moveDist) {
                    console.log('left')
                    var ev = new Event('carry-slide-left')
                    document.dispatchEvent(ev)
                }
            }

        }, true);
    }

    // 
    slide.addUpEvent = function(func, that) {
        document.addEventListener('carry-slide-up', func.bind(that))
    }
    // 
    slide.addDownEvent = function(func, that) {
        document.addEventListener('carry-slide-down', func.bind(that))
    }
    // 
    slide.addLeftEvent = function(func, that) {
        document.addEventListener('carry-slide-left', func.bind(that))
    }
    // 
    slide.addRightEvent = function(func, that) {
        document.addEventListener('carry-slide-right', func.bind(that))
    }
} (carry.slide)


// 摇一摇事件封装
/**/
carry.shake = {}
!function(shake) {
    var shake_tereshold = 1000;  
    var last_update = 0; 
    var x = y = z = last_x = last_y = last_z = 0;   
    var onDeviceMotion = function(eventData) {
        var curTime = new Date().getTime();  
        // carry.clog((curTime - last_update) + '')
        if ((curTime - last_update) < 100) {  
            return 
        }
        
        var diffTime = curTime - last_update;  
        last_update = curTime;  

        var acceleration = eventData.accelerationIncludingGravity;  // 移动加速度
        x = acceleration.x;   // 西向东
        y = acceleration.y;   // 南向北 
        z = acceleration.z;   // 垂直地面

        var speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000;  

        if (speed > shake_tereshold) {   
            
            var ev = new Event('carry-shake')
            document.dispatchEvent(ev)
        }  
        last_x = x;  
        last_y = y;  
        last_z = z;  
    }
    // 启动检测摇一摇事件
    shake.turnon = function() {
        if (window.DeviceMotionEvent) {  
            window.addEventListener('devicemotion', onDeviceMotion, false);  
        } 
        else {  
            alert('not support devicemotion');  
        }  
    }
    // 关闭检测
    shake.turnoff = function() {
        // if (window.DeviceMotionEvent) {  
        //     window.removeEventListener('devicemotion', onDeviceMotion, false);  
        // } 
    }

    shake.addTriggerEvent = function(func, that) {
        document.addEventListener('carry-shake', func.bind(that))
    }

}(carry.shake)


// mgr index
!function (mgr) {
    var loadScript = function (list, callback) {
        var loaded = 0;
        var loadNext = function () {
            loadSingleScript(list[loaded], function () {
                loaded++;
                if (loaded >= list.length) {
                    callback();
                }
                else {
                    loadNext();
                }
            })
        };
        loadNext();
    }
    var runEg = function (ver) {
        var xhr = new XMLHttpRequest();
        xhr.open('GET', './manifest.json?' + ver, true);
        xhr.addEventListener("load", function () {
            var manifest = JSON.parse(xhr.response);
            var list = manifest.initial.concat(manifest.game);
            loadScript(list, function () {
                // webgl \ canvas
                egret.runEgret({
                    renderMode: "webgl", audioType: 0, calculateCanvasScaleFactor: function (context) {
                        var backingStore = context.backingStorePixelRatio ||
                            context.webkitBackingStorePixelRatio ||
                            context.mozBackingStorePixelRatio ||
                            context.msBackingStorePixelRatio ||
                            context.oBackingStorePixelRatio ||
                            context.backingStorePixelRatio || 1;
                        return (window.devicePixelRatio || 1) / backingStore;
                    }
                });
            });
        });
        xhr.send(null);
    }

    // init game model and run
    mgr.SGameIsOk = false
    mgr.startGame = null // 回调函数 Main.ts 中定义
    mgr.run = function (ver) {
        // kone todo: carry bgm init here
        this.bgm.init(true, 'resource/bg.mp3')
        

        ver = ver || Date.parse(new Date())
        window.SGame = window.SGame || ({});
        window.SGame.isLoadingReady = true
        window.SGame.startGame = function () {
            if (this.SGameIsOk == true) {
                return
            }
            console.log("window.SGame.startGame")
            this.SGameIsOk = true
            this.startGame && this.startGame()
        }.bind(mgr)

        loadScript([
            'http://res.wx.qq.com/open/js/jweixin-1.1.0.js?' + ver
            // ,'http://cdn.aiwanpai.com/s/hdp-4.3.0.min.js?' + ver
            // ,'hdp_js/hdp_biz.js?' + ver
            // ,'interface/as.js?' + ver
            , 'interface/game.js?' + ver

            // ,'public/exif.js?' 
        ], function () {
        }.bind(this))

        //
        runEg(ver)
    }
} (carry)
