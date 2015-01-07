fb.m.Util.createNamespace("fdh.item");
fdh.item = (function() {
    var _nMaxCdt = 5;
    var _nTimer;
    function _useItem(htParam) {
        var nExecCnt = 0;
        _nTimer = setTimeout(function () {
            nExecCnt++;
            if (_useVitamin() == false && nExecCnt > 3) {
                _clearTimeout();
            }
        }, 1000);
    }

    function _useVitamin() {
        var htUseBtn = $("div.info a._use_btn[data-id='isitm_con_1'] span");
        if (htUseBtn.length <= 0) {
            return false;
        }

        _clearTimeout();
        htUseBtn.trigger("click");

        var nItemCnt = htUseBtn.parents("li").find("span._count").text();

        var aPlrList = fb.m.Team.getPlayerList("strt");
        var bSuccess = true;
        for (var i = 0; i < aPlrList.length; i++) {
            var htPlr = aPlrList[i];
            var nPlrCdt = htPlr.condition;
            var nPlrNo = htPlr.plrNo;

            for (var j = nPlrCdt, nUseItemCnt = 0; j < _nMaxCdt && nItemCnt >= nUseItemCnt; j++) {
                fb.m.Util.post("/gmc/item/apply", {
                                    plrNo : nPlrNo,
                                    itemId : 'isitm_con_1',
                                    strgSeq : 0
                                }, function(oRes) {
                                    // result
                                    var htRes = oRes.json();
                                    htRes.message = htRes.message.replace(/\\n/g, '\n');
                                    if(htRes.code == "SUCCESS") {
                                        htPlr.condition++;
                                    } else {
                                        bSuccess = false;
                                    }
                                });

                nUseItemCnt++;
                _sleep(200);
            }
        }

        alert("Done!");
        location.reload();
        return true;
    }

    function _getPlrCdt(htPlr) {
        var htPlrCdt = htPlr.find("span.ico.cdt");
        console.log(htPlr);
        console.log(htPlrCdt);

        if (htPlrCdt.hasClass("cdt_1")) {
            return 1;
        } else if (htPlrCdt.hasClass("cdt_2")) {
            return 2;
        } else if (htPlrCdt.hasClass("cdt_3")) {
            return 3;
        } else if (htPlrCdt.hasClass("cdt_4")) {
            return 4;
        } else if (htPlrCdt.hasClass("cdt_5")) {
            return 5;
        } else {
            return 0;
        }
    }


    function _clearTimeout() {
        clearTimeout(_nTimer);
    }

    function objToString (obj) {
        var sRes = '';
        for (var key in obj) {
            sRes += key + ' : ' + obj[key] + '\n';
        }
        return sRes;
    }

    function _sleep(milliseconds) {
      var start = new Date().getTime();
      for (var i = 0; i < 1e7; i++) {
        if ((new Date().getTime() - start) > milliseconds){
          break;
        }
      }
    }

    return {
        useItem : _useItem
    };
})();
