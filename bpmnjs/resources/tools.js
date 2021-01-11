/**
 * @description 全局功能封装
 * @author zr
 * @type {{registerFileDrop(*, *): void, saveBpmn(Object): void, handleDragOver(*): void, setColor(Object): void, downLoad(Object): void, upload(Object, Object, Object): void, handleFileSelect(*): void, setEncoded(Object, string, string): void, openFromUrl(Object, Object, Object, string): void, createDiagram(string, Object, Object): Promise<void>, getUrlParam: tools.getUrlParam}}
 */
import $ from 'jquery'

//const proHost = window.location.protocol + "//" + window.location.host;
//const href = window.location.href.split("bpmnjs")[0];
//const key = href.split(window.location.host)[1];
const proHost = "http://localhost";
const key = 8088;
const publicurl = proHost +":"+ key+"/";
const tools = {
    registerFileDrop(container, callback) {
        container.get(0).addEventListener('dragover', tools.handleDragOver, false);
        container.get(0).addEventListener('drop', tools.handleFileSelect, false);
    },
    /**
     * 获取地址栏参数
     * @param {string} value
     */
    getUrlParam: function (url) {
        var object = {};
        if (url.indexOf("?") !== -1) {
            var str = url.split("?")[1];
            var strs = str.split("&");
            for (var i = 0; i < strs.length; i++) {
                object[strs[i].split("=")[0]] = strs[i].split("=")[1]
            }
            return object
        }
        return object[url];
    },
    /**
     * 通过xml创建bpmn
     * @param {string} xml 创建bpms xml
     * @param {object} bpmnModeler bpmn对象
     * @param {object} container 容器对象
     */
    async createDiagram(xml, bpmnModeler, container) {
        try {
            await bpmnModeler.importXML(xml);
            container.removeClass('with-error').addClass('with-diagram');
        } catch (err) {
            container.removeClass('with-diagram').addClass('with-error');
            container.find('.error pre').text(err.message);
            console.error(err);
        }
    },
    /**
     * 通过Json设置颜色
     * @param {object} json json 字符串
     */
    setColor(json,bpmnModeler) {
        var modeling = bpmnModeler.get('modeling');
        var elementRegistry = bpmnModeler.get('elementRegistry')
        var elementToColor = elementRegistry.get(json.name);
        if(elementToColor){
            modeling.setColor([elementToColor], {
                stroke: json.stroke,
                fill: json.fill
            });
        }
    },
    /**
     * 保存bpmn对象
     * @param {object} bpmnModeler bpmn对象
     */
    async saveBpmn(bpmnModeler) {
        try {
            const result = await bpmnModeler.saveXML({format: true});
            const {xml} = result;
            console.log(publicurl);
            const param = {
                "stringBPMN": xml
            };
            $.ajax({
                url: publicurl + 'processDefinition/addDeploymentByString',
                type: 'POST',
                dataType: "json",
                data: param,
                // 先写死
                headers: {'Authorization': 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiLlhavmiJIiLCJjcmVhdGVkIjoxNjEwMjExMzMwNTMzLCJleHAiOjE2MTA4MTYxMzB9.kIB9w-qOCZxrtwpJ7b6QvafIZSDFQGRgEx1-twnPmMHcvIp09bP5c261rOasQJF1aUZwfU2v6q01NKfiCziYvQ'},
                success: function (result) {
                    if (result.code === 200) {
                        tools.syhide('alert')
                        alert('保存成功')
                    } else {
                        alert(result.message)
                    }
                },
                error: function (err) {
                    console.log(err)
                }
            });
        } catch (err) {
            return console.error('保存失败，请重试', err);
        }
    },
    /**
     * 下载bpmn
     * @param {object} bpmnModeler bpmn对象
     */
    async download(bpmnModeler) {
        const downloadLink = $("#downloadBpmn");
        try {
            const result = await bpmnModeler.saveXML({format: true});
            const {xml} = result;
            tools.setEncoded(downloadLink, 'diagram.bpmn', xml);
        } catch (err) {
            console.log(err);
            tools.setEncoded(downloadLink, 'diagram.bpmn', err);
        }
    },
    /**
     * 转码xml并下载
     * @param {object} link 按钮
     * @param {string} name 下载名称
     * @param {string} data base64XML
     */
    setEncoded(link, name, data) {
        const encodedData = encodeURIComponent(data);
        if (data) {
            link.addClass('active').attr({
                'href': 'data:application/bpmn20-xml;charset=UTF-8,' + encodedData,
                'download': name
            });
        } else {
            link.removeClass('active');
        }
    },
    /**
     * 上传bpmn
     * @param {object} bpmnModeler bpmn对象
     * @param {object} container 容器对象
     */
    upload(bpmnModeler, container) {
        const FileUpload = document.myForm.uploadFile.files[0];
        const fm = new FormData();
        fm.append('processFile', FileUpload);
        $.ajax({
            url: publicurl+'processDefinition/upload',
            type: 'POST',
            data: fm,
            async: false,
            contentType: false, //禁止设置请求类型
            processData: false, //禁止jquery对DAta数据的处理,默认会处理
            // 先写死
            headers:{'Authorization':'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiLlhavmiJIiLCJjcmVhdGVkIjoxNjEwMjExMzMwNTMzLCJleHAiOjE2MTA4MTYxMzB9.kIB9w-qOCZxrtwpJ7b6QvafIZSDFQGRgEx1-twnPmMHcvIp09bP5c261rOasQJF1aUZwfU2v6q01NKfiCziYvQ'},
            success: function (result) {
                const url = publicurl + 'bpmn/' + result.data;
                tools.openFromUrl(bpmnModeler, container, url)
            },
            error: function (err) {
                console.log(err)
            }
        });
    },
    /**
     * 打开xml  Url 地址
     * @param {object} bpmnModeler bpmn对象
     * @param {object} container 容器对象
     * @param {string} url url地址
     */
    openFromUrl(bpmnModeler, container, url) {
        $.ajax(url, { dataType: 'text' }).done(async function (xml) {
            try {
                await bpmnModeler.importXML(xml);
                container.removeClass('with-error').addClass('with-diagram');
            } catch (err) {
                console.error(err);
            }
        });
    },
    /**
     * 打开弹出框
     * @param id
     */
    syopen(id) {
        var dom = $("#" + id);
        this.sycenter(dom);
        dom.addClass(name);
        dom.show();
        var that = this;
        $(".sy-mask").fadeIn(300)
        setTimeout(function() {
            dom.removeClass(name)
        }, 300);

    },
    /**
     * 隐藏弹出框
     * @param id
     */
    syhide(id) {
        let dom;
        if (typeof id == "undefined") {
            dom = $(".sy-alert");
        } else {
            dom = $("#" + id);
        }
        var name = dom.attr("sy-leave");
        dom.addClass(name);
        $(".sy-mask").fadeOut(300);
        setTimeout(function() {
            dom.hide();
            dom.removeClass(name);
        }, 300)
    },
    /**
     * 弹出框居中
     * @param dom
     */
    sycenter(dom) {
        var mgtop = parseFloat(dom.height() / 2);
        dom.css({
            "top": "50%",
            "margin-top": "-" + mgtop + "px"
        })
    },
    /**
     * 判断是否是数组
     * @param value
     * @returns {arg is Array<any>|boolean}
     */
    isArrayFn(value){
        if (typeof Array.isArray === "function") {
            return Array.isArray(value);
        }else{
            return Object.prototype.toString.call(value) === "[object Array]";
        }
    },
    /**
     * 根据数据设置颜色
     * @param data
     * @returns {Array}
     */
    getByColor(data){

        var ColorJson=[]
        for(var k in data['highLine']){
            var par={
                "name": data['highLine'][k],
                "stroke":"green",
                "fill":"green"
            }
            ColorJson.push(par)
        }
        for(var k in data['highPoint']){
            var par={
                "name": data['highPoint'][k],
                "stroke":"gray",
                "fill":"#eae9e9"

            }
            ColorJson.push(par)
        }
        for(var k in data['iDo']){
            var par={
                "name": data['iDo'][k],
                "stroke":"green",
                "fill":"#a3d68e"
            }
            ColorJson.push(par)
        }
        for(var k in data['waitingToDo']){
            var par={
                "name": data['waitingToDo'][k],
                "stroke":"green",
                "fill":"yellow"
            }
            ColorJson.push(par)
        }
        return ColorJson
    }
}

export default tools;