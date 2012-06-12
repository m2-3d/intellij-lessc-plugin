/*
 * Copyright 2012 Andrew C. Dvorak.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.andydvorak.intellij.lessc.state;

import com.intellij.openapi.util.io.FileUtil;
import net.andydvorak.intellij.lessc.file.LessFile;
import org.lesscss.LessCompiler;
import org.lesscss.LessException;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

public class LessCompileJob {

    private final LessCompiler lessCompiler;
    private final LessFile lessFile;
    private final LessProfile lessProfile;
    private final Set<String> modifiedLessFileNames;

    private File cssTempFile = null;

    public LessCompileJob(LessFile lessFile, LessProfile lessProfile) {
        this.lessCompiler = new LessCompiler();
        this.lessFile = lessFile;
        this.lessProfile = lessProfile;
        this.modifiedLessFileNames = new LinkedHashSet<String>();
    }

    public LessCompileJob(final LessCompileJob otherCompileJob, final LessFile lessFile) {
        this.lessCompiler = otherCompileJob.getLessCompiler();
        this.lessFile = lessFile;
        this.lessProfile = otherCompileJob.getLessProfile();
        this.modifiedLessFileNames = otherCompileJob.getModifiedLessFileNames();
    }

    public LessCompiler getLessCompiler() {
        return lessCompiler;
    }

    public LessFile getLessFile() {
        return lessFile;
    }

    public LessProfile getLessProfile() {
        return lessProfile;
    }

    public Set<String> getModifiedLessFileNames() {
        return modifiedLessFileNames;
    }

    public int getNumModified() {
        return getModifiedLessFileNames().size();
    }

    public File getCssTempFile() throws IOException {
        if ( cssTempFile == null ) {
            cssTempFile = FileUtil.createTempFile("intellij-lessc-plugin.", ".css", true);
        }
        return cssTempFile;
    }

    public void compile() throws IOException, LessException {
        lessFile.compile(lessCompiler, getCssTempFile(), lessProfile.isCompressOutput());
    }
}
