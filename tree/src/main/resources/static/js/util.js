/** 2017/10/2 23:25
 *  作者:周志豪
 *  功能:全局公共js方法
 *  备注:
 */
define(['jquery'], function ($) {
    var util = {};
    util.ScrollFix = function (elem) {
        // Variables to track inputs
        var startY, startTopScroll;
        elem = document.querySelector(elem);
        // If there is no element, then do nothing
        if(!elem)
            return;

        // Handle the start of interactions
        elem.addEventListener('touchstart', function(event){
            startY = event.touches[0].pageY;
            startTopScroll = elem.scrollTop;

            if(startTopScroll <= 0)
                elem.scrollTop = 1;

            if(startTopScroll + elem.offsetHeight >= elem.scrollHeight)
                elem.scrollTop = elem.scrollHeight - elem.offsetHeight - 1;
        }, false);
    };
    util.ScrollFix('.container');
    return util;
});