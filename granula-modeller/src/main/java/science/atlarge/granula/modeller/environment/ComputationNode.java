/*
 * Copyright 2015 - 2017 Atlarge Research Team,
 * operating at Technische Universiteit Delft
 * and Vrije Universiteit Amsterdam, the Netherlands.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package science.atlarge.granula.modeller.environment;

import science.atlarge.granula.modeller.entity.Containable;
import science.atlarge.granula.modeller.platform.info.TimeSeriesInfo;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wing on 26-2-15.
 */
@XmlRootElement(name="ComputationNode")
public class ComputationNode extends Containable {
    Map<String, Process> processes;

    public ComputationNode(String name) {
        super();
        this.name = name;
        processes = new LinkedHashMap<>();
    }

    public ComputationNode() {
        super();
        this.name = "unspecified";
        processes = new LinkedHashMap<>();
    }

    @XmlElementWrapper(name="Processes")
    @XmlElementRef
    public List<Process> getProcesses() {
        return new ArrayList<Process>(processes.values());
    }

    public Process getProcess(String id) {
        if(processes.containsKey(id)) {
            return processes.get(id);
        } else {
            throw new IllegalStateException();
        }

    }

    public void addProcess(Process process) {
        processes.put(process.getName(), process);
    }

    public void addResourceInfo(TimeSeriesInfo timeSeriesInfo, String processId) {
        if(processes.containsKey(processId)) {
            Process process = getProcess(processId);
            process.addResourceInfo(timeSeriesInfo);
        } else {
            addProcess(new Process(processId));
            System.out.println(String.format("Warning: creating process %s at node %s", processId, name));
        }

    }

    public void addResourceInfo(TimeSeriesInfo timeSeriesInfo) {
        infos.put(timeSeriesInfo.getName(), timeSeriesInfo);
    }

//    public String exportBasic() {
//        return String.format("<ComputationNode name=\"%s\" uuid=\"%s\" />", name, uuid);
//    }
//
//    public String export() {
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append(String.format("<ComputationNode name=\"%s\" uuid=\"%s\">", name, uuid));
//
//        stringBuilder.append("<Processes>");
//        for (Process process : getProcesses()) {
//            stringBuilder.append(process.export());
//        }
//        stringBuilder.append("</Processes>");
//
//        stringBuilder.append(exportInfos());
//        stringBuilder.append(exportVisuals());
//
//        stringBuilder.append("</ComputationNode>");
//        return stringBuilder.toString();
//    }
}
