(function($) {
    var serverTimeClock = {
        hours: null,
        minutes: null,
        seconds: null
    };

    var localTimeOffset = 0;

    var observers = [];

    var increaseClock = function() {
        if (serverTimeClock.seconds < 59) {
            ++serverTimeClock.seconds;
        } else {
            serverTimeClock.seconds = 0;
            if (serverTimeClock.minutes < 59) {
                ++serverTimeClock.minutes;
            } else {
                serverTimeClock.minutes = 0;
                if (serverTimeClock.hours < 23) {
                    ++serverTimeClock.hours;
                } else {
                    serverTimeClock.hours = 0;
                }
            }
        }
    };

    var pad = function(s) {
        return s < 10 ? "0" + s : s;
    };

    var formatTime = function(hours, minutes, seconds) {
        return pad(hours) + ":" + pad(minutes) + ":" + pad(seconds);
    };

    var getTime = function() {
        return formatTime(serverTimeClock.hours, serverTimeClock.minutes, serverTimeClock.seconds);
    };

    var calculateLocalTimeOffset = function(serverHours, serverMinutes, serverSeconds) {
        var localInitTime = new Date();
        var serverInitTime = new Date(localInitTime.getFullYear(), localInitTime.getMonth(),
            localInitTime.getDate(), serverHours, serverMinutes, serverSeconds);
        return serverInitTime.getTime() - localInitTime.getTime();
    };

    var updateClock = function() {
        increaseClock();
        var formattedTime = getTime();
        $.each(observers, function(i, observer) {
            observer(formattedTime);
        });
    };

    var synchronizeClock = function() {
        var serverMilliseconds = new Date().getTime() + localTimeOffset;
        var serverTime = new Date(serverMilliseconds);
        serverTimeClock.hours = serverTime.getHours();
        serverTimeClock.minutes = serverTime.getMinutes();
        serverTimeClock.seconds = serverTime.getSeconds();
    };

    $.CRS = $.CRS || {};
    $.CRS.serverTime = {
        initClock: function(hours, minutes, seconds) {
            localTimeOffset = calculateLocalTimeOffset(hours, minutes, seconds);
            synchronizeClock();
            setInterval(updateClock, 1000);
            setInterval(synchronizeClock, 30000);
        },
        getTime: getTime,
        onChange: function(observer) {
            observers.push(observer);
        }
    };
}(jQuery));