/**
 * @description 全局功能封装
 * @author zr
 * @type {{registerFileDrop(*, *): void, saveBpmn(Object): void, handleDragOver(*): void, setColor(Object): void, downLoad(Object): void, upload(Object, Object, Object): void, handleFileSelect(*): void, setEncoded(Object, string, string): void, openFromUrl(Object, Object, Object, string): void, createDiagram(string, Object, Object): Promise<void>, getUrlParam: tools.getUrlParam}}
 */
import $ from 'jquery'

const proHost = window.location.protocol + "//" + window.location.host;
const href = window.location.href.split("bpmnjs")[0];
const key = href.split(window.location.host)[1];
const publicurl = proHost + key;
const tools = {
    /**
     * 下载bpmn
     * @param {object} bpmnModeler bpmn对象
     */
    download(bpmnModeler){
        var downloadLink = $("#downloadBpmn")
        bpmnModeler.saveXML({ format: true }, function (err, xml) {
            if (err) {
                return console.error('could not save BPMN 2.0 diagram', err);
            }
            tools.setEncoded(downloadLink, 'diagram.bpmn', err ? null : xml);
        });

    },
    /**
     * 转码xml并下载
     * @param {object} link 按钮
     * @param {string} name 下载名称
     * @param {string} data base64XML
     */
    setEncoded(link, name, data) {
        var encodedData = encodeURIComponent(data);
        if (data) {
            link.addClass('active').attr({
                'href': 'data:application/bpmn20-xml;charset=UTF-8,' + encodedData,
                'download': name
            });
        } else {
            link.removeClass('active');
        }
    }
}

export default tools;